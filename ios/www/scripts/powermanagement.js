var partialWakeLocked = false;

function dim() {
    if (!partialWakeLocked) {
        cordova.require('cordova/plugin/powermanagement').dim(pmSuccess, pmError);
        partialWakeLocked = true;
    }
}

function release() {
    if (partialWakeLocked) {
        cordova.require('cordova/plugin/powermanagement').release(pmSuccess, pmError);
        partialWakeLocked = false;
    }
}

function pmSuccess(){
    //Nothing further required
}

function pmError(msg){
    //Do not display any errors to the client.
}

cordova.define("cordova/plugin/powermanagement", function(require, exports, module) {
	var exec = require('cordova/exec');

	var PowerManagement = function() {};

	/**
	 * Acquire a new wake-lock (keep device awake)
	 *
	 * @param successCallback function to be called when the wake-lock was acquired successfully
	 * @param errorCallback function to be called when there was a problem with acquiring the wake-lock
	 */
	PowerManagement.prototype.acquire = function(successCallback,failureCallback) {
	    exec(successCallback, failureCallback, 'PowerManagement', 'acquire', []);
	}

	/**
	 * Release the wake-lock
	 *
	 * @param successCallback function to be called when the wake-lock was released successfully
	 * @param errorCallback function to be called when there was a problem while releasing the wake-lock
	 */
	PowerManagement.prototype.release = function(successCallback,failureCallback) {
	    exec(successCallback, failureCallback, 'PowerManagement', 'release', []);
	}

	/**
	 * Acquire a partial wake-lock, allowing the device to dim the screen
	 *
	 * @param successCallback function to be called when the wake-lock was acquired successfully
	 * @param errorCallback function to be called when there was a problem with acquiring the wake-lock
	 */
	PowerManagement.prototype.dim = function(successCallback,failureCallback) {
	    exec(successCallback, failureCallback, 'PowerManagement', 'acquire', [true]);
	}

	var powermanagement = new PowerManagement();
	module.exports = powermanagement;
});