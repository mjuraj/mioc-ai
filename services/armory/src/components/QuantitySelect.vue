<template>
  <div class="QuantitySelect">
    <div class="label">{{ this.label }}</div>
    <div class="value-group">
      <div class="format-text">{{ this.valueString }}</div>
      <div class="buttons">
        <b-button
          :class="this.classes"
          :type="this.type"
          :disabled="this.inputValue === this.maxValue"
          @click="add()"
        >+
        </b-button>
        <b-button
          :class="this.classes"
          :type="this.type"
          :disabled="this.inputValue === this.minValue"
          @click="subtract()"
        >-
        </b-button>
      </div>
    </div>
  </div>

</template>

<script>
import BaseValue from "armory-sdk/src/components/base/BaseValue.vue";

export default {
  name: "QuantitySelect",
  extends: BaseValue,
  props: {
    inputId: {
      type: String,
      required: true,
    },
    label: {
      type: String,
      required: false,
    },
    value: {
      type: Number,
      required: true,
    },
    maxValue: {
      type: Number,
      required: true,
    },
    minValue: {
      type: Number,
      required: true,
    },
    step: {
      type: Number,
      required: true,
    },
    format: {
      type: String,
      default: "{value}",
    },
    type: {
      type: String,
      default: "is-light",
    },
    classes: {
      type: Array,
      default: () => [],
    },
  },
  computed: {
    valueString() {
      return this.format.replace("{value}", this.inputValue);
    },
  },
  methods: {
    add() {
      if (this.inputValue < this.maxValue) {
        this.inputValue += this.step;
      }
    },
    subtract() {
      if (this.inputValue > this.minValue) {
        this.inputValue -= this.step;
      }
    },
  },
};
</script>

<style scoped lang="scss">
.QuantitySelect {
  display: flex;
  justify-content: space-between;
}

.label {
  display: flex;
  justify-content: left;
  align-items: center;
  margin-bottom: 0px !important;
}

.value-group {
  display: flex;
  justify-content: right;
  align-items: baseline;
  gap: 0.5rem;
}

.buttons {
  display: grid;
  grid-template-columns: 2.5rem 2.5rem;
  grid-gap: 0.25rem;
}

.buttons button {
  border-radius: 2px;
  box-shadow: none;
  padding: 0.8em;
  margin-right: unset !important;
}
</style>
