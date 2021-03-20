var datepickerOptions = {

    format: 'yyyy-mm-dd',
    yearRange: 80

}

document.addEventListener('DOMContentLoaded', DOMinit);

function DOMinit(){
    var elems = document.querySelectorAll('.datepicker');
    var instances = M.Datepicker.init(elems, datepickerOptions);
}