
window.onload = function (){
    const userid = $('input[name="userid"]').val();
    const eventSource = new EventSource("/connect/"+userid);
    eventSource.addEventListener('NewMsg',(e) =>{
        const {data : receivedConnectData} = e;
        console.log('connect event data : ',receivedConnectData);
        $("#alarm").append('<span class="position-absolute top-0 start-100 translate-middle p-2 bg-danger border border-light rounded-circle"><span class="visually-hidden"></span></span>');
    });
};

$(document).ready(function (){


$("#alarm").click(function (){
   console.log("hello");
   $("#alarm span").remove();
   $(".popup-alarm").css({"display":"block"});
});

    $("#popup-alarm-out").on("click",function(){
        $(".popup-alarm").css({"display":"none"});
    });
});