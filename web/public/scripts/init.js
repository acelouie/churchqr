var datepickerOptions = {

    format: 'yyyy-mm-dd',
    yearRange: 80

}

document.addEventListener('DOMContentLoaded', function() {

    var elems = document.querySelectorAll('.datepicker');
    var instances = M.Datepicker.init(elems, datepickerOptions);

  });