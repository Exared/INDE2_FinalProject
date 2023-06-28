const express = require('express');
const app = express();

app.use(express.json());
app.use(function(req, res, next) {
    res.header("Access-Control-Allow-Origin", "*");
    res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
    next();
  });
let all_alerts = []

app.get("/alert", (req, res) => {
    if (all_alerts.length !== 0) {
        let send_alert = all_alerts.pop()
        console.log("envoie d'alerte au front : ",send_alert)
        res.send(send_alert)
    }
    res.end()
})

app.post("/alert", (req, res) => {
    console.log("Alert recu : ",req.body)
    all_alerts.push(req.body)
    res.end()
})

console.log("listenning in 4000")
app.listen(4000)