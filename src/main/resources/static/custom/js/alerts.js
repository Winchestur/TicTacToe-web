var NotificationEventManager = (function () {
    var notificationsContainer = $('#alertsContainer');

    function initHideNotificationEvent() {
        notificationsContainer.on('click', '.hide-noti-box', function (eventArgs) {
            console.log($(this).parent().find('span:first').text()); //TODO remove this line
            if (window.onNotiClose) {
                window.onNotiClose($(this).parent());
            }
        });
    }

    function initNotificationClickEvent() {
        notificationsContainer.on('click', '.noti-box', function (eventArgs) {
            if (eventArgs.target !== this) {
                return;
            }

            if (window.onNotiClick) {
                window.onNotiClick($(this));
            }
        });
    }

    function initEvents() {
        initHideNotificationEvent();
        initNotificationClickEvent();
    }

    return {initEvents: initEvents};
})();

$(function () {
    NotificationEventManager.initEvents();
});