/**
 * 
 */
  window.onload= function() {
	  
	  window.fbAsyncInit = function() {
	    FB.init({
	      appId      : '1070815552954243',
	      xfbml      : true,
	      version    : 'v2.7'
	    });
	  };
	
	  (function(d, s, id){
	     var js, fjs = d.getElementsByTagName(s)[0];
	     if (d.getElementById(id)) {return;}
	     js = d.createElement(s); js.id = id;
	     js.src = "//connect.facebook.net/en_US/sdk.js";
	     fjs.parentNode.insertBefore(js, fjs);
	   }(document, 'script', 'facebook-jssdk'));
	  
	document.getElementById('shareBtn').onclick = function() {
	  FB.ui({
	    method: 'share',
	    display: 'popup',
	    href: 'http://blogs.pjw6193.tech/',
	  }, function(response){});
	}
}