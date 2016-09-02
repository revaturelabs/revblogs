function checkPass()
{
    //Store the password field objects into variables ...
    var oldPassword = document.getElementById('oldPassword');
    var newPassword = document.getElementById('newPassword');
    //Store the Confimation Message Object ...
    var message = document.getElementById('confirmMessage');
    //Set the colors we will be using ...
    var goodColor = "#66cc66";
    var badColor = "#ff6666";
    //Compare the values in the password field 
    //and the confirmation field
    if(oldPassword.value == newPassword.value){
        //The passwords match. 
        //Set the color to the good color and inform
        //the user that they have entered the correct password 
        newPassword.style.backgroundColor = goodColor;
        message.style.color = goodColor;
        message.innerHTML = "Passwords Match!"
    }else{
        //The passwords do not match.
        //Set the color to the bad color and
        //notify the user.
        newPassword.style.backgroundColor = badColor;
        message.style.color = badColor;
        message.innerHTML = "Passwords Do Not Match!"
    }
}  