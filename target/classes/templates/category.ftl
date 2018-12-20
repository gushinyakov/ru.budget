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


        #list-categories, #category-info {
            width: 490px;
            box-shadow: 0 0 5px grey;
            padding: 10px 20px;
            margin-top: 30px;
            border: 2px solid silver;
            border-radius: 5px;
            float: left;
            margin-right: 30px;
            max-height: 500px;
            overflow: auto;
        }

        form button {
            width: 100px;
            height: 46px;
            position: absolute;
            margin: 0 10px;
            border: 3px solid silver;
            font-size: 16px;
        }

        #list-categories a {
            font-size: 20px;
            float: right;
            text-decoration: none;
            margin: 0 10px;
        }

        form input {
            width: 400px;
            height: 40px;
            border: 3px solid silver;
            font-size: 20px;
            color: gray;
            padding: 0 10px;
        }
        .category-item {
            margin-top: 10px;
        }
        .category-item .line {
            margin-top: 10px;
            border: 1px solid silver;
        }

        .category-item p {
            display: inline;
            font-size: 20px;
        }

        #category-info .category-item p {
            display: block;
        }
        #category-info .category-item p span {
            font-style: italic;
        }
        #category-info h3 {
            text-align: center;
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
        <a href="/period"><span>Период</span></a>
    </div>

    <!-- Content
    ============================================= -->
    <section id="content">

        <div class="content-wrap">

            <div class="container clearfix">


                <!-- Form to add category -->

                <div>

                    <form action="/category" method="post">
                        <input class="typeahead sm-form-control tt-input" name="category-add" type="text" placeholder="Добавить категорию" >
                        <button type="submit" class="button button-3d button-rounded button-green"><i class="icon-repeat"></i>Добавить</button>
                    </form>

                </div>


                <!-- Categories container -->

                <div id="list-categories">

                    <#if listCategory?has_content>

                        <#list listCategory as category>

                        <div class="category-item">
                            <p>Категория: ${category.name}</p>
                            <a href="/show/${category.id}">Транзакции</a>
                            <a href="/delete/${category.id}">Удалить</a>
                        </div>

                        </#list>

                    </#if>


                </div>

                <div id="category-info">

                    <#if categoryHeader?has_content>
                        <h3>Категория ${categoryHeader} </h3>
                    </#if>

                    <#if categoryInfo?has_content>
                        <#list categoryInfo as category>
                            <div class="category-item">
                                <p>Транзакция: <span>${category.name}</span></p>
                                <p>Дата: <span>${category.date}</span></p>
                                <p>Сумма: <span>${category.value}</span></p>
                                <div class="line"></div>
                            </div>
                        </#list>
                    <#else>
                        <p> По данной категории нет транзакций </p>
                    </#if>
                </div>

            </div>

        </div>

    </section> <!-- #Content end -->


</div>


</body>
</html>