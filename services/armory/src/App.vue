<script>
import Armory from "armory-sdk/src/Armory";
import Slider from "./components/Slider.vue";
import StartScreenGraphics from "./components/StartScreenGraphics.vue";
import EndScreenGraphics from "./components/EndScreenGraphics.vue";

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
      };
    },
  },
};
</script>
