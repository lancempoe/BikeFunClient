function setupTimePicker() {
    $('#timepicker').mobiscroll().time({
        theme: 'android',
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
    $('#clear').click(function () {
        alert("hi");
        $('#demo').val('');
        return false;
    });
}