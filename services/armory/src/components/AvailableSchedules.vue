<template>
  <div class="AvailableSchedule">
    <div
      v-for="(options, dateStr) in groupedOptions"
      id="schedules-container"
      :key="options"
    >
      <div id="schedule-date">
        {{ dateStr }}
      </div>
      <div class="date-options">
        <button
          :class="[checked(option) ? 'button active' : 'button']"
          v-for="option in options"
          :key="option"
          @click="chooseTime(option)"
        >
          <span>
            {{
              option.toLocaleTimeString("en-US", {
                hour: "numeric",
                minute: "numeric",
                hour12: true,
                timeZone: defaultTimezone,
              })
            }}
          </span>
        </button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "AvailableSchedule",
  props: {
    inputId: String,
    options: Array,
  },
  methods: {
    chooseTime(option) {
      const optionStr = option.toISOString();
      if (Object.keys(this.$store.state.inputs).length === 0) {
        this.$store.commit("setInputValue", {
          inputId: this.inputId,
          value: optionStr,
        });
      } else {
        const value =
          this.$store.state.inputs[this.inputId] === optionStr
            ? null
            : optionStr;
        this.$store.commit("setInputValue", {
          inputId: this.inputId,
          value: value,
        });
      }
    },

    checked(option) {
      return (
        this.$store.state.inputs[this.inputId] !== undefined &&
        this.$store.state.inputs[this.inputId] === option.toISOString()
      );
    },
  },
  computed: {
    defaultTimezone() {
      return process.env.VUE_APP_DEFAULT_TIME_ZONE;
    },

    groupedOptions: function () {
      let grouped = {};
      for (let option of this.options) {
        const dateObj = new Date(option.start);
        const formattedDateStr = Intl.DateTimeFormat("en-US", {
          day: "numeric",
          month: "short",
          year: "numeric",
          timeZone: this.defaultTimezone,
        }).format(dateObj);
        if (grouped[formattedDateStr] === undefined)
          grouped[formattedDateStr] = [];
        grouped[formattedDateStr].push(dateObj);
      }
      return grouped;
    },
  },
};
</script>

<style lang="scss" scoped>
@import "~@/assets/css/skin.scss";

div.date-options {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
}

#schedule-date {
  padding-bottom: 0.6rem;
  padding-top: 1.5rem;
  color: $primary-dark;
  font-size: 1.2rem;
}

button {
  background-color: $primary-light;
}

button.active {
  background-color: $primary;
  color: #fff;
}
</style>
