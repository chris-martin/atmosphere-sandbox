(function($) {

  var content, input, status, subSocket;

  var request = {
    url: document.location.toString() + 'chat',
    contentType: 'application/json',
    logLevel: 'debug',
    transport: 'long-polling',
    onOpen: function(response) {
      content.html($('<p/>', { text: 'Atmosphere connected using ' + response.transport }));
      status.text('go: ');
    },
    onReconnect: function(request, response) {
      $.atmosphere.info("Reconnecting")
    },
    onMessage: function(response) {
      var message = response.responseBody;
      try {
        var json = JSON.parse(message);
      } catch (e) {
        console.log('bad JSON: ', message);
        return;
      }

      addMessage(json.message, new Date());

      function addMessage(m, t) {
        content.append('<p>@ ' +
          + (t.getHours() < 10 ? '0' + t.getHours() : t.getHours()) + ':'
          + (t.getMinutes() < 10 ? '0' + t.getMinutes() : t.getMinutes())
          + ': ' + m + '</p>');
      }
    },
    onError: function(response) {
      content.html($('<p/>', { text: 'Problem?' }));
    }
  };

  function onKeyDown(e) {
    if (e.keyCode === 13) {
      var msg = $(this).val();
      subSocket.push(JSON.stringify({ message: msg }));
      $(this).val('');
    }
  }

  $(function() {
    content = $('#content');
    input = $('#input');
    status = $('#status');
    subSocket = $.atmosphere.subscribe(request)
    input.keydown(onKeyDown);
  });

})(jQuery);