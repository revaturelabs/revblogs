$(document).ready(function() {
      // scroll nav logic
      var yPos = $(window).scrollTop();
      var maxDelta = 5;
      var didScroll = false;
      var mouseY = 0;
      $(window).scroll(function(e) {
        didScroll = true;
      });
      $(document).mousemove(function(e) {
        mouseY = e.clientY;
      });
      setInterval(function () {
        if (mouseY <= 70) {
          $(".page-navigation").fadeIn(200);
        }
        else if (didScroll) {
          var newYPos = $(window).scrollTop();
          if (Math.abs(yPos-newYPos) >= maxDelta) {
            if (newYPos > yPos) {
              $(".page-navigation").fadeOut(200);
            }
            else {
              $(".page-navigation").fadeIn(200);
            }
          }
          yPos = newYPos;
        }
        didScroll = false;
      }, 250);
    });