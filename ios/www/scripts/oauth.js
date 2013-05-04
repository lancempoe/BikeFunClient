//OAuth Javascript
var iabRef = null;
var googleAuthCode = null;
function getGoogleAuthCode() {
    return googleAuthCode;
}

var OAUTHURL    =   "https://accounts.google.com/o/oauth2/auth?";
var VALIDURL    =   "https://www.googleapis.com/oauth2/v1/tokeninfo?access_token=";
var SCOPE       =   "https://www.googleapis.com/auth/userinfo.profile https://www.googleapis.com/auth/userinfo.email";
var CLIENTID    =   "963091932519.apps.googleusercontent.com";
//String REDIRECT    =   "http://localhost";//Window.Location.getPath();
//var REDIRECT    =   "urn:ietf:wg:oauth:2.0:oob";//Window.Location.getPath();
var REDIRECT    =   "http://localhost";


var LOGOUT      =   "http://accounts.google.com/Logout";
var TYPE        =   "code";
var googleLoginUrl =   OAUTHURL + "scope=" + SCOPE + "&client_id=" + CLIENTID + "&redirect_uri=" + REDIRECT + "&response_type=" + TYPE;
var acToken;
var tokenType;
var expiresIn;
var user;
var loggedIn    =   false;

function doGoogleForOauth(name) {
    <!--alert("Hello from JavaScript, " + name);-->

    try {
        iabRef = window.open(googleLoginUrl, "_blank", "location=no");
        //iabRef.addEventListener('loadstart', onLoadStart);
        iabRef.addEventListener('loadstop', onLoadStop);
        iabRef.addEventListener('exit', onExit);

    } catch (e) {
        alert(e);
    }
}

    function onLoadStart(event) {
        <!--alert(event.type + ' - ' + event.url);-->
    }

    function onLoadStop(event) {
        //alert(event.type + ' - ' + event.url);
        //if(event.url == "http://pagethatuwant1/closeInAppBrowser.html"){
        if(/^http:\/\/localhost/i.test(event.url)) {
            if(/^error/i.test(event.url)) {
                alert("login error");
            } else {
                var secondPos = event.url.indexOf("code=");
                <!--alert("secondPos="+secondPos+", length="+event.url.length);-->
                googleAuthCode = event.url.substring(secondPos+"code=".length, event.url.length);


            <!--tokenType = gup(event.url, 'token_type');-->
                    <!--expiresIn = gup(event.url, 'expires_in');-->

                    <!--alert("event.url("+event.url+"),"+-->
                          <!--"acToken("+acToken+"),"+-->
                          <!--"tokenType("+tokenType+"),"+-->
                          <!--"code("+googleCode+")");-->

                    iabRef.close();
            }
        } else {
            <!--alert(event.url);-->
        }
    }

    function onExit(event) {
        <!--alert(event.type);-->
        if (iabRef) {
            iabRef.removeEventListener('loadstart', onLoadStart);
            iabRef.removeEventListener('loadstop', onLoadStop);
            iabRef.removeEventListener('exit', onExit);
            iabRef = null;
        }
    }