<template>
  <div class="Select select" :class="this.classes">
    <select
      v-model="customInputValue"
      :class="{ 'has-text-grey-light': customInputValue === placeholder }"
    >
      <option
        v-for="option in this.expandedOptions()"
        :key="option"
        :value="option"
        :disabled="option === placeholder"
        :hidden="option === placeholder"
      >
        {{ option }}
      </option>
    </select>
  </div>
</template>

<script>
import BaseValue from "armory-sdk/src/components/base/BaseValue.vue";

export default {
  name: "ArmorySelect",
  extends: BaseValue,
  props: {
    options: {
      type: Array,
      required: true
    },
    placeholder: {
      type: String,
      required: false
    },
    classes: {
      type: Array,
      default: () => ["is-fullwidth"],
    },
  },
  methods: {
    expandedOptions() {
      return [this.placeholder, ...this.options];
    },
  },
  computed: {
    customInputValue: {
      get() {
        return this.inputValue || this.placeholder;
      },
      set(value) {
        if (value !== this.placeholder) {
          this.inputValue = value;
        } else this.inputValue = null;
      },
    },
  },
};
</script>

<style scoped>
</style>
