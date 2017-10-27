var i = 0;

function onSendComplete() {
    scrollToBottom();
    
    var input = $('#message_form\\:content');
    input.focus();
};

function onReceiveComplete() {
    //TODO: make this conditional on scrollheight having changed.
    scrollToBottom();
}

function scrollToBottom() {
    //Scroll message board to the bottom.
    var board = $('#message_form\\:message_board');
    board.scrollTop(board.prop("scrollHeight"));
}