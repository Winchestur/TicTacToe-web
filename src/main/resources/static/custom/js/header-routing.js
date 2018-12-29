/**
 * This is used to highlight the link on the navigation bar that is currently opened.
 */
$(function () {
    var headerLinks = $('#navbarSupportedContent').find('a');

    headerLinks.each(function (number, link) {
        link = $(link);
        if (link.attr('href') === CURRENT_URL) {
            link.parent().addClass('active');
        }
    });
});