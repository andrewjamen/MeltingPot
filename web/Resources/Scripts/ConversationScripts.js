
$(document).ready(function() {
    scrollToBottom();
});

function onSendComplete() {
    scrollToBottom();
    
    var input = $('#message_form\\:content');
    input.focus();
};

function onReceiveComplete() {
    scrollToBottom();
}

function scrollToBottom() {
    //Scroll message board to the bottom.
    //TODO: Make this conditional new new messages having been received or posted.
    var board = $('#message_form\\:message_board');
    board.scrollTop(board.prop("scrollHeight"));
}