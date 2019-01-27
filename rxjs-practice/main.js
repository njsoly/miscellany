let obs = Rx.Observable.interval(1000);

obs.subscribe(value => console.log("Subscriber: " + value));

