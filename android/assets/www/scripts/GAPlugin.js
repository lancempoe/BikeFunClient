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

(function(){
    var cordovaRef = window.PhoneGap || window.Cordova || window.cordova;

    function GAPlugin() { }

    // initialize google analytics with an account ID and the min number of seconds between posting
    //
    // id = the GA account ID of the form 'UA-00000000-0'
    // period = the minimum interval for transmitting tracking events if any exist in the queue
    GAPlugin.prototype.init = function(success, fail, id, period) {
        return cordovaRef.exec(success, fail, 'GAPlugin', 'initGA', [id, period]);
    };

    // log an event
    //
    // category = The event category. This parameter is required to be non-empty.
    // eventAction = The event action. This parameter is required to be non-empty.
    // eventLabel = The event label. This parameter may be a blank string to indicate no label.
    // eventValue = The event value. This parameter may be -1 to indicate no value.
    GAPlugin.prototype.trackEvent = function(success, fail, category, eventAction, eventLabel, eventValue) {
        return cordovaRef.exec(success, fail, 'GAPlugin', 'trackEvent', [category, eventAction, eventLabel, eventValue]);
    };


    // log a page view
    //
    // pageURL = the URL of the page view
    GAPlugin.prototype.trackPage = function(success, fail, pageURL) {
        return cordovaRef.exec(success, fail, 'GAPlugin', 'trackPage', [pageURL]);
    };

    // Set a custom variable. The variable set is included with
    // the next event only. If there is an existing custom variable at the specified
    // index, it will be overwritten by this one.
    //
    // value = the value of the variable you are logging
    // index = the numerical index of the dimension to which this variable will be assigned (1 - 20)
    //  Standard accounts support up to 20 custom dimensions.
    GAPlugin.prototype.setVariable = function(success, fail, index, value) {
        return cordovaRef.exec(success, fail, 'GAPlugin', 'setVariable', [index, value]);
    };

    GAPlugin.prototype.exit = function(success, fail) {
        return cordovaRef.exec(success, fail, 'GAPlugin', 'exitGA');
    };

    cordovaRef.addConstructor(function() {
        if(!window.plugins) {
            window.plugins = {};
        }
        if(!window.plugins.gaPlugin) {
            window.plugins.gaPlugin = new GAPlugin();
        }
    });
})(); /* End of Temporary Scope. */