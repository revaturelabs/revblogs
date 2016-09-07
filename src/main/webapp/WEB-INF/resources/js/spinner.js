/**
 * 
 */
$(document).ready(function(){
$("#upload").click(function () {     
    //call show loading function here
    showLoading();
    $.ajax({
        type: "POST",
        url: "upload-picture",
        enctype: 'multipart/form-data',
        data: {
            file: file
        },
        success: function () {
            //call hide function here
            hideLoading();
            alert("Data has been Uploaded: ");
        },
        error  : function (a) {//if an error occurs
            hideLoading();
            alert("An error occured while uploading data.\n error code : "+a.statusText);
        }
    });
});
function showLoading(){
	document.getElementById("loading").style = "visibility: visible";
	}
	function hideLoading(){
	document.getElementById("loading").style = "visibility: hidden";
	}
});
