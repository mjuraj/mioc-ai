<template>
  <div class="SimpleSwitch" :class="this.classes">
    <b-switch
      v-model="inputValue"
      :rounded="true"
      :outlined="false"
      :left-label="this.leftLabel"
      :type="this.type"
      @change="handleChange()"
      >{{ label }}
    </b-switch>
  </div>
</template>

<script>
import BaseValue from "armory-sdk/src/components/base/BaseValue.vue";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Toggle",
  extends: BaseValue,
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
  },
};
</script>

<style scoped lang="scss">
.switch {
  display: flex;
  justify-content: space-between;
}

::v-deep span.control-label {
  font-weight: bold !important;
}
</style>
