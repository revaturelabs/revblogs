/**
 * 
 */

function poll(fn, callback, errback, timeout, interval) {
    var endTime = Number(new Date()) + (timeout || 2000);
    interval = interval || 100;

    (function p() {
            // If the condition is met, we're done! 
            if(fn()) {
                callback();
            }
            // If the condition isn't met but the timeout hasn't elapsed, go again
            else if (Number(new Date()) < endTime) {
                setTimeout(p, interval);
            }
            // Didn't match and too much time, reject!
            else {
                errback(new Error('timed out for ' + fn + ': ' + arguments));
            }
    })();
}

window.onload = function() {
	poll(
		function() {
			return document.getElementById('lightbox').offsetWidth > 0;
		},
		function() { // Done, success callback
			console.log('yay polled');
		},
		function() {
			// Error, failure callback
			console.log('boo not polled');
		}
	);
	console.log('hello world!');
}
