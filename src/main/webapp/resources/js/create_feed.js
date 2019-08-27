"use strict";

const $resultBar = $('#creationResult');
const $feedUrl = $('#feedUrl');
const $rules = $('#rules');
const $newsFeedsTable = $('#newsFeedsTable');
const $info = $('#info');

const urlPattern = new RegExp('(https?:\/\/(?:www\.|(?!www))[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\.[^\s]{2,}|www\.[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\.[^\s]{2,}|https?:\/\/(?:www\.|(?!www))[a-zA-Z0-9]\.[^\s]{2,}|www\.[a-zA-Z0-9]\.[^\s]{2,})');
const rulesPattern = new RegExp('[a-zA-Z][:]{0,4}');

var feedTypes = [];
var newsFeeds = [];
var errorMessages = [];

$info.hide();

function mapFeedTypeNames(source) {
    $.each(source, function (_, elem) {
        $.each(feedTypes, function (_, type) {
            if (elem.feedType === type.id) {
                elem.feedType = type.description;
            }
        });
    });
    newsFeeds = source;
}

function fillNewsFeedsTable(result) {
    $('#newsFeedsTable tr:not(:first)').remove();
    mapFeedTypeNames(result);
    $.each(result, function (_, elem) {
        $newsFeedsTable.append('<tr><td>' + elem.id + '</td><td>' + elem.url + '</td><td>' + elem.feedType + '</td><td>'
            + elem.domain + '</td><td>' + elem.newsPrefix + '</td><td>' + elem.bodyTag
            + '</td><td>' + elem.bodyWrapperClass + '</td><td><input type=button value="Удалить"'
            + ' class="btn btn-sm btn-danger js-feed-del" id=' + elem.id + '></td></tr>');
    });
}

function printErrors() {
    $resultBar.addClass('alert-warning');
    $resultBar.text(errorMessages.join('; '));
    errorMessages = [];
}

function getFeedTypes() {
    $.ajax({
        url: 'feed_types/get.do',
        type: 'POST',
        success: function (result) {
            feedTypes = result;
        },
        error: function () {
            $resultBar.addClass('alert-danger');
            $resultBar.text('Ошибка получения типов новостных лент!');
        }
    });
}
getFeedTypes();

function getNewsFeeds() {
    $.ajax({
        url: 'news_feeds/get.do',
        type: 'POST',
        success: function (result) {
            newsFeeds = result;
            fillNewsFeedsTable(newsFeeds);
        },
        error: function () {
            $resultBar.addClass('alert-danger');
            $resultBar.text('Ошибка получения новостных лент!');
        }
    });
}
getNewsFeeds();

function validate() {
    var feedUrl = $feedUrl.val();
    var rules = $rules.val();
    var validationErrorMessages = [];
    if (!urlPattern.test(feedUrl)) {
        validationErrorMessages.push('Неверно задан формат URL');
    }
    if (!rulesPattern.test(rules)) {
        validationErrorMessages.push('Неверно задан формат правил');
    }
    return validationErrorMessages;
}

function mapFeedType(typeName) {
    var type = {};
    $.each(feedTypes, function (_, elem) {
        if (typeName === elem.description) {
            type.typeId = elem.id;
            type.argsAmount = elem.argumentsAmount;
        }
    });
    return type;
}

function parseRules() {
    var rulesCollection = $rules.val().split('|');
    var feedType = mapFeedType(rulesCollection[0]);
    if (!feedType) {
        errorMessages.push('Некорректный тип новостной ленты!');

        return null;
    } else if (rulesCollection.length !== feedType.argsAmount) {
        errorMessages.push('Неверное количество аргументов для типа новостной ленты!');

        return null;
    }else if (rulesCollection[1] && !urlPattern.test(rulesCollection[1])) {
        errorMessages.push('Неверный формат домена!');

        return null;
    } else {
        var result = {};
        var feedUrl = $feedUrl.val();
        result.feedUrl = feedUrl.slice(-1) === '/' ? feedUrl.substring(0, feedUrl.length - 1) : feedUrl;
        result.feedType = feedType.typeId;
        result.domain = rulesCollection[1] ? rulesCollection[1] : null;
        if (result.domain) {
            result.domain = result.domain.slice(-1) === '/' ? result.domain.substring(0, result.domain.length - 1) : result.domain;
        }
        result.prefix = rulesCollection[2] ? rulesCollection[2] : null;
        result.bodyTag = rulesCollection[3] ? rulesCollection[3] : null;
        result.bodyWrapperClass = rulesCollection[4] ? rulesCollection[4] : null;

        return result;
    }
}

$('#addButton').click(function () {
    errorMessages = validate();
    if (errorMessages[0]) {
        printErrors();
    } else {
        var feedParams = parseRules();
        if (!feedParams) {
            printErrors();
        } else {
            $.ajax({
                url: 'newsfeed/add.do',
                type: 'POST',
                data: feedParams,
                success: function (result) {
                    getNewsFeeds();
                    $resultBar.removeClass('alert-warning');
                    $resultBar.addClass('alert-success');
                    $resultBar.text(result);
                },
                error: function () {
                    $resultBar.addClass('alert-danger');
                    $resultBar.text('Ошибка добавления ленты новостей!');
                }
            });
        }
    }
});

$newsFeedsTable.on("click", '.js-feed-del', function () {
    $.ajax({
        url: 'newsfeed/delete.do',
        type: 'POST',
        data: {
            id: $(this).attr("id")
        },
        success: function (result) {
            getNewsFeeds();
            $resultBar.removeClass('alert-warning');
            $resultBar.addClass('alert-success');
            $resultBar.text(result);
        },
        error: function () {
            $resultBar.addClass('alert-danger');
            $resultBar.text('Ошибка удаления ленты новостей!');
        }
    });
});

$('#backButton').click(function () {
    $(location).attr('href', '/');
});

$('#info-badge').click(function () {
    $info.toggle();
});
