$('#tags').children('span').each(function () {
    alert(this.value); // "this" is the current element in the loop
});
