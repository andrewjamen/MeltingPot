
/*
 * Starts the message board scrolled to the bottom.
 */
$(function() {
    scrollToBottom();
});

/*
 * Scrolls message board to the bottom and focuses the input text field.
 * Call after a message is sent and the board is rerendered.
 */
function onSendComplete() {
    scrollToBottom();
    
    var input = $('#message_form\\:content');
    input.focus();
};

/*
 * Scroll message board to the bottom.
 * Call whenever messages are rendered.
 */
function scrollToBottom() {
    var board = $('#message_form\\:message_board');
    board.scrollTop(board.prop("scrollHeight"));
}