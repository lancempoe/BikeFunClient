function setupDatePicker() {
//Mobiscroll.com
//http://demo.mobiscroll.com/calendar#mode=clickpick&display=modal&theme=android-ics+light
    $('#datepicker').mobiscroll().calendar({
        theme: 'android-ics light',
        display: 'bottom',
        controls: ['calendar'],
        onSelect: function(valueText,inst){
            $('#datepicker').text(valueText);
        }
    });
    $('#datepicker').click(function(){
                           $('#datepicker').mobiscroll('show');
                           window.scrollTo(0, 50);
        return false;
    });
};

function getDatePickerText() {
    return $('#datepicker').text();
};
