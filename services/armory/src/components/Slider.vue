<template>
  <b-field>
    <b-slider v-model="slideValue" :min="1" :max="10" ticks></b-slider>
  </b-field>
</template>

<script>
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Slider",
  props: {
    inputId: String,
    required: {
      type: Boolean,
      default: false,
    },
  },
  mounted: function () {
    const initialValue = this.slideValue || 5;

    this.$store.commit("setInputValue", {
      inputId: this.inputId,
      value: {
        value: initialValue,
      },
    });

    this.required &&
      this.$store.commit("setInputValidation", {
        inputId: this.inputId,
        valid: this.isValid(initialValue),
      });
  },
  computed: {
    slideValue: {
      get() {
        return this.$store.state.inputs[this.inputId]
          ? this.$store.state.inputs[this.inputId].value
          : 5;
      },
      set(value) {
        this.$store.commit("setInputValue", {
          inputId: this.inputId,
          value: {
            value,
          },
        });

        this.required &&
          this.$store.commit("setInputValidation", {
            inputId: this.inputId,
            valid: this.isValid(value),
          });
      },
    },
  },
  methods: {
    isValid: (value) => value !== null,
  },
};
</script>
