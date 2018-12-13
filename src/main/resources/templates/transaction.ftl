<!doctype html>
<html dir="ltr" lang="en-US">
<head>

    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <meta name="author" content="SemiColonWeb" />

    <!-- Document Title
    ============================================= -->
    <title>Категории</title>

    <style>
        #nav {
            position: absolute;
            right: 50px;
            padding: 20px;
        }

        #nav a {
            margin: 20px;
            font-size: 22px;
            border: none;
            text-decoration: none;
        }

        #content {
            margin: 50px;
            padding: 30px;
        }

        form button {
            width: 100px;
            height: 46px;
            position: absolute;
            margin: 0 10px;
            border: 3px solid silver;
            font-size: 16px;
        }

        form input, form select {
            width: 150px;
            height: 40px;
            border: 3px solid silver;
            font-size: 20px;
            color: gray;
            padding: 0 10px;
        }

        form select {
            height: 45px;
        }

        #transaction-list {
            width: 490px;
            height: 600px;
            overflow: auto;
            box-shadow: 0 0 5px grey;
            padding: 10px 20px;
            margin-top: 30px;
            border: 2px solid silver;
            border-radius: 5px;
        }

        .transaction-info span {
            float: left;
        }

        .transaction-list {
            padding: 10px;
            font-size: 20px;
            border-bottom: 2px solid grey;
        }

        .sum {
            float: right !important;
        }

        .dashed-line {
            width: 200px;
            border: 2px dashed silver;
            margin-top: 10px;
            float: left;
            margin-left: 20px;
        }

        .transaction-category {
            padding: 10px;
        }

        .transaction-info {
            padding: 20px;
            margin-bottom: 20px;
        }
    </style>

</head>
<body class="stretched">

    <!-- Document Wrapper
    ============================================= -->
    <div id="wrapper" class="clearfix">


        <!-- Navigation menu
        ============================================= -->

        <div id="nav">
            <a href="/category"><span>Категории</span></a>
            <a href="/transaction"><span>Транзакции</span></a>
        </div>

        <!-- Content
        ============================================= -->
        <section id="content">

            <div class="content-wrap">

                <div class="container clearfix">

                    <form action="/addTransaction" method="post">
                        <input class="typeahead sm-form-control tt-input" name="transaction-date" type="date" placeholder="Укажите дату" >
                        <input class="typeahead sm-form-control tt-input" minlength="3" maxlength="30" name="transaction-name" type="text" placeholder="Название транзакции" >
                        <input class="typeahead sm-form-control tt-input" name="transaction-value" max="1000000" min="-1000000" type="number" placeholder="Введите сумму" >
                        <select name="transaction-category" id="transaction-category">
                            <option value="default">Не определена</option>
                            <#list categoryOptions as category>
                                <option value="${category}">${category}</option>
                            </#list>
                        </select>
                        <button type="submit" class="button button-3d button-rounded button-green"><i class="icon-repeat"></i>Добавить</button>
                    </form>


                    <div id="transaction-list">

                        <div class="transaction-item">

                            <#list transactions?keys as key>

                                <div class="transaction-list">

                                    <div class="transaction-date">
                                        <strong>${key}</strong>
                                        <span class="sum total-sum">0</span>
                                    </div>

                                    <#list transactions[key]?keys as transaction>

                                        <div class="transaction-category">
                                            <strong>${transaction}</strong>
                                            <span class="sum category-sum" data-categorySum-Total="${transaction}">0</span>
                                        </div>

                                        <#list transactions[key][transaction] as map>

                                            <#list map?keys as v>

                                                <div class="transaction-info">
                                                    <span>${v}</span>
                                                    <div class="dashed-line"></div>
                                                    <span class="sum transaction-sum" data-categorySum="${transaction}">${map[v]}</span>
                                                </div>

                                            </#list>
                                        </#list>
                                    </#list>
                                </div>
                            </#list>

                        </div>
                    </div>

                </div>

            </div>

        </section>

    </div>

    <script
            src="https://code.jquery.com/jquery-3.3.1.min.js"
            integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
            crossorigin="anonymous"></script>
    <script type="text/javascript">
        $(function(){
            var dtToday = new Date();

            var month = dtToday.getMonth() + 1;
            var day = dtToday.getDate();
            var year = dtToday.getFullYear();

            if(month < 10)
                month = '0' + month.toString();
            if(day < 10)
                day = '0' + day.toString();

            var maxDate = year + '-' + month + '-' + day;
            $('input[name="transaction-date"]').attr('max', maxDate);
        });

        $(window).ready(function() {


            $('.transaction-list').each(function(){

                var transaction = $(this);
                var total = 0;

                $(this).find('.transaction-category').each(function () {

                    $(this).each(function () {

                        var category = $(this);

                        $('#transaction-category option').each(function () {

                            var option = $(this).val();
                            var categorySum = category.find('[data-categorySum-Total="' + option + '"]');

                            transaction.find('[data-categorySum="' + option + '"]').each(function () {
                                categorySum.html(Number.parseInt(categorySum.text()) + Number.parseInt($(this).text()));
                            });

                        });

                    });


                });

                transaction.find('.category-sum').each(function () {
                    total += Number.parseInt($(this).text());
                });

                transaction.find('.total-sum').html(total);
            });





        });
    </script>

</body>
</html>