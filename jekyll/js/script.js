
// Tagging functionality
(function($) {
  $.fn.tagList = function(tagContainer) {
    var tags = {
      'tagElements': new Array(),
      'articleElements': new Array(),
      'tagsToArticleElements': new Object(),
      'tagsToTagElement': new Object(),
    };

    // Collect all tags and their corresponding articles
    this.children().each(function(idx, article) {
      $(article).find('.tag').each(function(idx, tag) {
        var tagName =  $(tag).text().trim();
        if(tags.tagsToArticleElements[tagName] === undefined) {
          // Previously unknown tag
          tags.tagElements.push(createTag(tags, tagName, tagContainer));
          tags.tagsToArticleElements[tagName] = new Array();
        }
        tags.articleElements.push(article);
        tags.tagsToArticleElements[tagName].push(article);
      });
    });
  }

  function createTag(tags, tagName, tagContainer) {
    var tagElement = $('<li></li>');
    tagElement.text(tagName);
    tagElement.click(function() {
      onTagClick(tags, tagName);
    });
    tags.tagElements.push(tagElement);
    tags.tagsToTagElement[tagName] = tagElement;
    $(tagContainer).append(tagElement);
  }

  function onTagClick(tags, tag) {
    if($(tags.tagsToTagElement[tag]).hasClass('active')) {
      deactivateTags(tags.tagElements);
      showArticles(tags.articleElements);
    } else {
      deactivateTags(tags.tagElements);
      activateTag(tags.tagsToTagElement[tag]);
      hideArticles(tags.articleElements);
      showArticles(tags.tagsToArticleElements[tag]);
    }
  }

  function deactivateTags(tags) {
    $(tags).each(function(idx, tag) {
      $(tag).removeClass('active');
    });
  }

  function activateTag(tag) {
    $(tag).addClass('active');
  }

  function hideArticles(articles) {
    $(articles).each(function(idx, article) {
      $(article).hide();
    });
  }

  function showArticles(articles) {
    $(articles).each(function(idx, article) {
      $(article).show();
    });
  }
}(jQuery));





(function($) {
  $.fn.greeter = function() {
    var greetingElement = this;
    var i = 0;
    var greetings = [ "Hola, soy", "Hi, I'm", "Hallo, ich bin", "Salut, je suis" ];

    function nextGreeting() {
      $(greetingElement).shuffleLetters({
        'text': greetings[i],
        'callback': function() {
          i++;
          if(i > greetings.length - 1) i = 0;
          setTimeout(nextGreeting, 5000);
        }
      });
    }

    nextGreeting();
  }
}(jQuery));
