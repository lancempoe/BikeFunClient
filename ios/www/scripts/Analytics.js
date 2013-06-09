// More details here: http://docs.phonegap.com/en/2.5.0/cordova_events_events.md.html#Events
document.addEventListener("deviceready",onDeviceReady,false);
document.addEventListener("resume", onResume, false);
document.addEventListener("pause", onPause, false);

var gaPlugin;
var isReady = false;

function onDeviceReady() {
    if (navigator.userAgent.match(/(iPhone|iPod|iPad|Android)/)) {
        gaPlugin = window.plugins.gaPlugin;
        gaPlugin.init(gaSuccess, gaError, "UA-41361242-3", 10);
        trackPage("Application Started");
        isReady = true;
    }
}

function onResume() {
    if(isReady) {
        gaPlugin = window.plugins.gaPlugin;
        gaPlugin.init(gaSuccess, gaError, "UA-41361242-3", 10);
        trackPage("Application Resumed");
    }
}

function onPause() {
    if(isReady) { 
        gaPlugin.exitGA(gaPlugin, gaError);
    }
}

function trackPage(pageBeingTracked) {
    if(isReady) {
        gaPlugin.trackPage(gaSuccess, gaError, pageBeingTracked);
    }
}

function gaSuccess(){
    //Nothing further required
}

function gaError(msg){
    //Do not display any errors to the client.
}