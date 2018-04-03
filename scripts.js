/* scripts.js */
try { console.debug("loading scripts.js."); } catch (e){ }

// definitions to save some 
if(typeof body == "undefined") 
	body = document.body;
if(typeof DEBUG == "undefined") 
	DEBUG = false;

__initLoggerShortcuts = function(){
	if(typeof console != "undefined"){
		if(typeof console.info != "undefined"){	
			info = function(msg){
				console.info(msg);
			};
		}
		if(typeof console.debug != "undefined"){
			debug = function(msg){
				console.debug(msg);	
			};
		}
		if(typeof console.log != "undefined"){
			log = function(msg){
				console.log(msg);
			};
		}
		if(typeof console.warn != "undefined"){
			warn = function(msg){
				console.warn(msg);
			};
		}
		if(typeof console.error != "undefined"){
			error = function(msg){
				console.error(msg);
			};
		}
	}
}

__initLoggerShortcuts();

toggleVisibility = function(x){
	if(typeof x == "undefined"){
		console.error("toggleVisibiity(): undefined param.");
		return;
	}
	// if the object is an array or list
	if(typeof x.length != "undefined"){
		for(i = 0; i < x.length; i++){
			o=x[i];
			o.style["display"] = (o.style["display"]=="none") ? "" : "none";
			
		}
	}
	// if the object is just one thing.
	else {
		x.style["display"] = (x.style["display"]=="none") ? "" : "none";
	
	}
}

setVisibility = function(x,visible){
	if(typeof x == "undefined") return;
	x.style["display"] = (typeof visible == "undefined" || visible == true) ? "" : "none";
}

// Prototype for general objects (? -- TODO TEST IT)
if("undefined" == typeof Object.prototype.setVisibility){
	Object.prototype.setVisibility = function(visible){
		setVisibility(this, visible);
	}
}

/** This is a singleton object to perform document creation
 * 	(and possibly some more stuff later).
 */
sir = {
	lastReturn : null, 
	debug : (typeof DEBUG !== "undefined" ? DEBUG : true),
};

/** 
 * Creates a new TEXT node, and if defined, the first argument 
 * becomes its content.
 * @returns the new text node (does not add it to the document).
 */
sir.textNode = function(msg){
	var node;
	if (typeof msg !== "undefined"){
		node = document.createTextNode(msg);
		if(DEBUG) debug("creating text node with text \"" + msg + "\".");
	}
	else{
		node = document.createTextNode("");
		if(DEBUG) debug("creating text node with empty string.");
	} 
	this.lastReturn = node;
	return node;
}

/** 
 * Appends a div to document.body, and returns that new DIV.
 * One optional argument: becomes the HTML inside the new DIV.
 */
sir.appendDiv = function(html){
	var div;
	div = document.createElement("div");
	if (typeof html !== "undefined"){
		div.innerHTML = html;
		if(DEBUG) 
			console.debug("setting innerHTML of div to \"" + html + "\".");
	}
	else{
		if(DEBUG) 
			console.debug("creating empty DIV and appending.");
	}
	document.body.appendChild(div);
	this.lastReturn = div;
	return div;

}


try {
	console.debug("done loading scripts.js."); 
} 
catch (e) {}

