"use strict";

const $resultBar = $('#resultBar');
const $newsTable = $('#newsTable');
const $filterText = $('#filterText');
const $spinnerContainer = $('#spinner-container');

$spinnerContainer.hide();

$('#feedManagingButton').click(function () {
    $(location).attr('href', 'create_feed.do');
});

function fillNewsTable(result) {
    $('#newsTable tr:not(:first)').remove();
    $.each(result, function (_, elem) {
        $newsTable.append('<tr><td>' + elem.title + '</td><td>' + elem.body + '</td><td>');
    });
}

function getNews() {
    $spinnerContainer.show();
    $.ajax({
        url: '/news/get.do',
        type: 'POST',
        success: function (result) {
            fillNewsTable(result);
            $spinnerContainer.hide();
        },
        error: function () {
            $resultBar.addClass('alert-danger');
            $resultBar.text('Ошибка получения списка новостей!');
            $spinnerContainer.hide();
        }
    });
}
getNews();

$('#refreshNews').click(function () {
    $spinnerContainer.show();
    $.ajax({
        url: '/news/parse.do',
        type: 'POST',
        success: function (result) {
            fillNewsTable(result);
            $spinnerContainer.hide();
        },
        error: function () {
            $spinnerContainer.hide;
            $resultBar.addClass('alert-danger');
            $resultBar.text('Ошибка обновления списка новостей!');
        }
    });
});

$('#newsFilter').click(function () {
    $.ajax({
        url: '/news/search.do',
        type: 'POST',
        data: {
            titlePart: '%' + $filterText.val() + '%'
        },
        success: function (result) {
            fillNewsTable(result);
        },
        error: function () {
            $resultBar.addClass('alert-danger');
            $resultBar.text('Ошибка фильтрации новостей!');
        }
    });
});