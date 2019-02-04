var GameInviteManager = function () {

    var gameInviteConstants = {
        USER_FIELD_NAME: 'player',
        GAME_INVITE_FIELD_NAME: 'invite',
    };

    function makeCall(url, data, successCallback, failCallback) {
        $.ajax({
            url: url,
            type: 'POST',
            data: data,
            success: successCallback,
            error: failCallback
        });
    }

    function createInviteData(inviteId) {
        var inviteData = {};
        inviteData[gameInviteConstants.GAME_INVITE_FIELD_NAME] = inviteId;
    }

    function sendGameInvite(username, successCallback, failCallback) {
        var playerData = {};
        playerData[gameInviteConstants.USER_FIELD_NAME] = username;

        makeCall(
            "/invites/invite",
            playerData,
            successCallback,
            failCallback
        );
    }

    function cancelInvite(inviteId, successCallback, failCallback) {
        makeCall(
            "/invites/cancel",
            createInviteData(inviteId),
            successCallback,
            failCallback
        );
    }

    function acceptInvite(inviteId, successCallback, failCallback) {
        makeCall(
            "/invites/accept",
            createInviteData(inviteId),
            successCallback,
            failCallback
        );
    }

    function declineInvite(inviteId, successCallback, failCallback) {
        makeCall(
            "/invites/cancel",
            createInviteData(inviteId),
            successCallback,
            failCallback
        );
    }

    return {
        sendGameInvite: sendGameInvite,
        cancelInvite: cancelInvite,
        acceptInvite: acceptInvite,
        declineInvite: declineInvite,
    };
}();

$(function () {
    window.onInviteSend = function (username) {
        GameInviteManager.sendGameInvite(username, function (succ) {
            NotificationViewManager.showNotification(NotificationViewManager.createNotification(locale.inviteHasBeenSentTo + username, notificationSeverity.SUCCESS), 5000);
        }, function (err) {
            console.log(err);
        });
    }
});