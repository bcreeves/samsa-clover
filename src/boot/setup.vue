<template>
  <view class="container">
    <app></app>
  </view>
</template>


<script>
import Vue from "vue-native-core";
import { StackNavigator } from "vue-native-router";
import { VueNativeBase } from "native-base";

import App from "../App.vue";

Vue.use(VueNativeBase);

import store from '../store';
Vue.prototype.$store = store;

export default {
  components: { App },
//   data: function() {
//     return {
//       isAppReady: false
//     };
//   },
  created: function() {
    // this.loadFonts();
  },
//   methods: {
//     loadFonts: async function() {
//       try {
//         this.isAppReady = false;
//         await Expo.Font.loadAsync({
//           Roboto: require("native-base/Fonts/Roboto.ttf"),
//           Roboto_medium: require("native-base/Fonts/Roboto_medium.ttf"),
//           Ionicons: require("@expo/vector-icons/fonts/Ionicons.ttf")
//         });
//         this.isAppReady = true;
//       } catch (error) {
//         console.log("some error occured", error);
//         this.isAppReady = true;
//       }
//     }
//   }
};
</script>

<style>
.container {
  flex: 1;
}
</style>
