<template>
    <div>
        <div id="main-content" class="container">
            <div class="row">
                <div class="col-md-6">
                    <form class="form-inline">
                        <div class="form-group">
                            <input v-model="simulations" placeholder="how many simulations?">
                            <button id="connect" class="btn btn-default" type="submit" :disabled="connected == true" @click.prevent="connect">Connect</button>
                            <button id="send" class="btn btn-default" type="submit" @click.prevent="executeTest">Send WebSocket Message</button>
                            <button v-on:click="toCSV" type="submit" :disabled="timeList.length != simulations" >toCSV</button>
                            <button id="disconnect" class="btn btn-default" type="submit" :disabled="connected == false" @click.prevent="disconnect">Disconnect</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";

export default {
  name: "websocketdemo",
  props: ['delay'],
  data() {
    return {
      received_messages: [],
      connected: false,
        simulations: 0,
        timeList: []
    };
  },
  methods: {
    executeTest(){
        for (let i = 0; i < this.simulations; i++) { 
         this.send();
         this.sleep(this.delay);
        }
    },
    send() {
        let name = Math.random().toString(36).substring(7);
        let content = Math.random().toString(36).substring(7);
        let startTime = performance.now();

        let message = { 'name': name, 'content' : content, 'startTime' : startTime};
        if (this.stompClient && this.stompClient.connected) {
            const msg = message;
            this.stompClient.send("/app/hello", JSON.stringify(msg), {});
        }
    },
    connect() {
      this.socket = new SockJS("http://localhost:8080/gs-guide-websocket");
      this.stompClient = Stomp.over(this.socket);
      this.stompClient.connect(
        {},
        frame => {
          this.connected = true;
          this.stompClient.subscribe("/topic/greetings", tick => {
            this.received_messages.push(JSON.parse(tick.body).content);
            this.timeList.push(performance.now() - JSON.parse(tick.body).startTime);
          });
        },
        error => {
          console.log(error);
          this.connected = false;
        }
      );
    },
    disconnect() {
      if (this.stompClient) {
        this.stompClient.disconnect();
      }
      this.connected = false;
    },
    tickleConnection() {
      this.connected ? this.disconnect() : this.connect();
    },
    toCSV(){
        let csvContent = "data:text/csv;charset=utf-8,";
        this.timeList.forEach(function(rowArray){
            let row = rowArray +=  "\r\n";
            csvContent += row;
        }); 

        var encodedUri = encodeURI(csvContent);
        var link = document.createElement("a");
        link.setAttribute("href", encodedUri);
        link.setAttribute("download", "websocket_data.csv");
        document.body.appendChild(link); // Required for FF

        link.click();
    },
    sleep(milliseconds) {
        var start = new Date().getTime();
        for (var i = 0; i < 1e7; i++) {
            if ((new Date().getTime() - start) > milliseconds){
            break;
            }
        }
    }
  },
  mounted() {
    // this.connect();
  }
};
</script>

<style scoped>
</style>