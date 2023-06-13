<script>
import Armory from "armory-sdk/src/Armory";
import Slider from "./components/Slider.vue";
import StartScreenGraphics from "./components/StartScreenGraphics.vue";
import EndScreenGraphics from "./components/EndScreenGraphics.vue";
import TablePreview from "./components/TablePreview.vue";
import ArmorySelect from "./components/ArmorySelect.vue";
import QuantitySelect from "./components/QuantitySelect.vue";
import NestedToggle from "./components/NestedToggle.vue";
import AvailableSchedules from "./components/AvailableSchedules.vue";

export default {
  name: "App",
  extends: Armory,
  methods: {
    getCustomTemplates() {
      return {};
    },
    getCustomComponents() {
      return {
        Slider,
        StartScreenGraphics,
        EndScreenGraphics,
        TablePreview,
        Select: ArmorySelect,
        Toggle,
        QuantitySelect,
        NestedToggle,
        AvailableSchedules,
      };
    },
  },
};
</script>
