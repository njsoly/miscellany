/* scripts.js */
if(typeof console !== "undefined"){	console.debug("loading scripts.js.");}

defineThings = function(){
	if(typeof console != "undefined" && typeof console.info != "undefined"){	info = function(msg){	console.info(msg);	}}
	if(typeof console != "undefined" && typeof console.debug != "undefined"){	debug = function(msg){	console.debug(msg);	}}
	if(typeof console != "undefined" && typeof console.log != "undefined"){log = function(msg){console.log(msg);}}
	if(typeof console != "undefined" && typeof console.warn != "undefined"){warn = function(msg){console.warn(msg);}}
	if(typeof console != "undefined" && typeof console.error != "undefined"){error = function(msg){console.error(msg);}}
	if(typeof body == "undefined"){body = document.body;}
	if(typeof DEBUG == "undefined"){DEBUG = false;}
	if(typeof tmp == "undefined"){tmp = null;}
}
defineThings();

toggleVisibility = function(x){
	if(typeof x == "undefined"){
		debug("toggleVisibiity(): undefined param.");
		return;
	}
	if(typeof x.length != "undefined"){
		for(i = 0; i < x.length; i++){
			o=x[i];
			o.style["display"] = (o.style["display"]=="none") ? "" : "none";
			
		}
	}
	else {
		x.style["display"] = (x.style["display"]=="none") ? "" : "none";
	
	}
}

setVisibility = function(x,visible){
	if(typeof x == "undefined"){
		return;
	}
	x.style["display"] = (typeof visible == "undefined" || visible == true) ? "" : "none";
}

if("undefined" == typeof Object.prototype.setVisibility){
	Object.prototype.setVisibility = function(visible){
		setVisibility(this, visible);
	}
}
sir = {
	lastReturn : null, 
	debug : (typeof DEBUG !== "undefined" ? DEBUG : true),
};

sir.textNode = function(msg){
	var node;
	if (typeof msg !== "undefined"){
		node = document.createTextNode(msg);
		debug("creating text node with text \"" + msg + "\".");
	}
	else{
		node = document.createTextNode("");
		debug("creating text node with empty string.");
	} 
	this.lastReturn = node;
	return node;
}
sir.appendDiv = function(html){
	var div;
	div = document.createElement("div");
	if (typeof html !== "undefined"){
		div.innerHTML = html;
		console.debug("setting innerHTML of div to \"" + html + "\".");
	}
	else{
		console.debug("sir.appendDiv(): creating empty DIV and appending.");
	}
	document.body.appendChild(div);
	this.lastReturn = div;
	return div;

}


if(typeof console !== "undefined"){	console.debug("done loading scripts.js.");}