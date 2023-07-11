<template>
  <div class="TablePreview" :class="classes">
    <div class="header" v-if="title">
      <h5>{{ title }}</h5>
    </div>
    <table
      :class="{
      'column-based': columns !== undefined,
      'key-based': keys !== undefined,
      }"
    >
      <tr v-if="columns">
        <th
          v-for="columnId of Object.keys(columns)"
          :key="'header-' + columnId"
        >
          {{ columns[columnId] }}
        </th>
      </tr>
      <tr v-for="(row, index) in groupedData" :key="'row-' + index"
        :class="{'round': row[0] % 5 === 0 && index >= 5}">
        <td v-for="(cell, index) of row" :key="'cell-' + index">
          {{ cell }}
        </td>
      </tr>
    </table>
  </div>
</template>

<script>
export default {
  name: "TablePreview",
  props: {
    title: {
      type: String,
      required: false,
    },
    columns: {
      type: Object,
      required: false,
    },
    keys: {
      type: Object,
      required: false,
    },
    data: {
      type: [Object, String],
      required: true,
    },
    classes: {
      type: Array,
      default: () => [],
    },
  },
  computed: {
    processedData() {
      return typeof this.data === "string" ? JSON.parse(this.data) : this.data;
    },
    groupedData() {
      let grouped = [];
      if (this.columns !== undefined) {
        for (let column of Object.keys(this.columns)) {
          let index = 0;
          for (let value of this.processedData[column]) {
            if (grouped.length <= index) grouped[index] = [];
            grouped[index].push(this.formatNumber(value));
            index++;
          }
        }
        return grouped;
      } else if (this.keys !== undefined) {
        for (let column of Object.keys(this.keys)) {
          grouped.push([
            this.keys[column],
            this.formatNumber(this.processedData[column]),
          ]);
        }
      }
      return grouped;
    },
  },
  methods: {
    formatNumber: (value) => {
      if (isNaN(Number(value))) return value;
      value = Number(value);
      if (Math.abs(value) > 100000000)
        return (value / 1000000000).toFixed(2) + "B";
      if (Math.abs(value) > 100000) return (value / 1000000).toFixed(2) + "M";
      if (Math.abs(value) > 100) return (value / 1000).toFixed(2) + "K";
      return value === 0 ? "-" : value;
    },
  },
};
</script>

<style scoped lang="scss">
@import "~@/assets/css/skin.scss";

.TablePreview.expanded {
  margin: auto -26px;
}

table {
  font-size: 12px;
  font-family: "Roboto Mono", monospace;
}

th {
  font-weight: bold;
}

td,
th {
  text-align: right !important;
}

table.key-based td:nth-child(1) {
  text-align: left !important;
}

.TablePreview.emphasized tr.round {
  background-color: #f5f5f5;
  font-weight: bold;
  color: $primary;
}
</style>
