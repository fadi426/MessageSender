<template>
  <div id="app">
    <img alt="Vue logo" src="./assets/logo.png" />
    <label>Delay between each call</label>
    <input v-model="delay" placeholder="how much delay?">
    <RESTMessageSender v-bind:delay = delay></RESTMessageSender>
    <WebSocketMessageSender v-bind:delay = delay></WebSocketMessageSender>
  </div>
</template>

<script>
import RESTMessageSender from "./components/RESTMessageSender.vue";
import WebSocketMessageSender from "./components/WebSocketMessageSender.vue";

export default {
  name: "app",
  components: {
    RESTMessageSender,
    WebSocketMessageSender
  },
  data(){
    return{
      delay: 0
    }
  }
};
</script>

<style>
#app {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
