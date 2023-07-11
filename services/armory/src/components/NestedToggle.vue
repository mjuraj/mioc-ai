<template>
  <div class="NestedToggle" :class="this.classes">
    <b-switch
      v-model="inputValue"
      :rounded="true"
      :outlined="false"
      :left-label="this.leftLabel"
      :type="this.type"
      >{{ label }}
    </b-switch>
    <div v-if="this.inputValue" class="nested-container">
      <div
        v-for="(component, index) in this.childComponents"
        :key="index + component.componentName + '-parent'"
        class="component content"
      >
        <component :is="component.componentName" v-bind="component.params">
        </component>
      </div>
    </div>
  </div>
</template>

<script>
import ArmorySelect from "@/components/ArmorySelect.vue";
import ArmorySwitch from "@/components/Toggle.vue";
import ArmoryButton from "@/components/QuantitySelect.vue";
import BaseValue from "armory-sdk/src/components/base/BaseValue.vue";
import GenericTemplate from "armory-sdk/src/templates/GenericTemplate.vue";
import App from "@/App.vue";

export default {
  name: "NestedToggle",
  extends: BaseValue,
  components: {
    Select: ArmorySelect,
    SimpleSwitch: ArmorySwitch,
    Button: ArmoryButton,
  },
  props: {
    label: {
      type: String,
      required: false,
    },
    value: {
      type: Boolean,
      default: false,
    },
    leftLabel: {
      type: Boolean,
      default: false,
    },
    state: {
      type: Boolean,
      default: false,
    },
    type: {
      type: String,
      default: "is-primary",
    },
    classes: {
      type: Array,
      default: () => ["is-fullwidth"],
    },
    childComponents: {
      type: Array,
      required: true,
    },
  },
  beforeMount() {
    const allComponents = this.getAllComponents();
    for (const componentId in allComponents)
      this.$options.components[componentId] = allComponents[componentId];
  },
  methods: {
    getAllComponents() {
      return {
        ...GenericTemplate.components,
        ...App.methods.getCustomComponents(),
      };
    },
  },
};
</script>

<style scoped lang="scss">
.switch {
  display: flex;
  justify-content: space-between;
}

.nested-container {
  width: 100%;
  padding-left: 0.6rem;
  padding-top: 1.5rem;
}

.nested-container > .component {
  margin-bottom: 0.75rem;
}

span.control-label {
  font-weight: bold;
}

::v-deep span.control-label {
  font-weight: bold !important;
}
</style>
