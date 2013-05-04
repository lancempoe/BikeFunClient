function setupDatePicker() {
//Mobiscroll.com
//http://demo.mobiscroll.com/calendar#mode=clickpick&display=modal&theme=android-ics+light
    $('#datepicker').mobiscroll().calendar({
        theme: 'android-ics light',
        display: 'modal',
        controls: ['calendar'],
        onSelect: function(valueText,inst){
            $('#datepicker').text(valueText);
        }
    });
    $('#datepicker').click(function(){
        $('#datepicker').mobiscroll('show');
        return false;
    });
};

function getDatePickerText() {
    return $('#datepicker').text();
};
