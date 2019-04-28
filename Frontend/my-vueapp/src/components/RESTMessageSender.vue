<template>
  <div>
      <input v-model="simulations" placeholder="how many simulations?">
      <button v-on:click="executeTest">Send REST message</button>
      <button v-on:click="toCSV" type="submit" :disabled="timeList.length != simulations" >toCSV</button>
  </div>
</template>

<script>
import axios from 'axios';

export default {
    props: ['delay'],
  data() {
    return {
        simulations: 0,
        timeList: []
    };
  },
  methods: {
      executeTest(){
        for (let i = 0; i < this.simulations; i++) { 
         this.sendMessage();
         this.sleep(this.delay);
        }
      },
    sendMessage() {
       
        return new Promise((resolve) => {
            let name = Math.random().toString(36).substring(7);
            let content = Math.random().toString(36).substring(7);
            let startTime = performance.now();

            axios.post('http://localhost:8080/rest', { 'name': name, 'content' : content, 'startTime' : startTime})
            .then((response) => {
                if (response.data.name.length > 0){
                    this.timeList.push(performance.now() - response.data.startTime);
                }
                resolve()
            });
        });
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
        link.setAttribute("download", "rest_data.csv");
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
};
</script>

<style>

</style>
