function setupTimePicker() {
//Mobiscroll.com
//http://demo.mobiscroll.com/datetime/time#mode=mixed&display=modal&theme=android-ics+light
    $('#timepicker').mobiscroll().time({
        theme: 'android-ics light',
        display: 'modal',
        mode: 'mixed',
        onSelect: function(valueText,inst){
            $('#timepicker').text(valueText);
        }
    });
    $('#timepicker').click(function(){
        $('#timepicker').mobiscroll('show');
        return false;
    });
}