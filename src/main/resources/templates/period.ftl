<!doctype html>
<html dir="ltr" lang="en-US">
<head>

    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <meta name="author" content="SemiColonWeb" />

    <!-- Document Title
    ============================================= -->
    <title>Период</title>

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

        #chart {
            width: 400px;
            height: 400px;
            margin: 20px auto;
        }

        .date-button {
            min-width: 100px;
            height: 46px;
            margin: 0 10px;
            border: 3px solid silver;
            font-size: 20px;
            cursor: pointer;
            padding: 10px;
            text-decoration: none;
        }

        #date-buttons {
            width: 310px;
            margin: 50px auto 20px;
        }


        .dashed-line {
            width: 400px;
            border: 2px dashed silver;
            float: left;
            margin: 40px 0;
        }

        .category-info {
            width: 100%;
            height: 100px;
        }
        .category-info p {
            font-size: 26px;
            float: left;
        }

        .category-info .category-name {
            width: 200px;
        }
        .category-info .category-sum {
            float: right;
        }

        #info-list {
            width: 700px;
            margin: 0 auto;
            border-top: 3px solid silver;
        }

        #total-costs {
            text-align: center;
            font-size: 26px;
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

                        <div id="date-buttons">

                            <a href="/period" class="date-button">День</a>
                            <a href="/periodLastWeek" class="date-button">Неделя</a>
                            <a href="/periodLastMonth" class="date-button">Месяц</a>

                        </div>

                        <div id="total-costs">Расходы: ${total}</div>

                        <div id="chart">
                            <canvas id="myChart" width="400" height="400"></canvas>
                        </div>

                        <div id="info-list">

                            <#list transactions?keys as key>

                                <div class="category-info">
                                    <p class="category-name">${key}</p>
                                    <div class="dashed-line"></div>
                                    <p class="category-sum">${transactions[key]}</p>
                                </div>

                            </#list>


                        </div>
                    </div>

                </div>

            </section>

    </div>

    <script
            src="https://code.jquery.com/jquery-3.3.1.min.js"
            integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
            crossorigin="anonymous"></script>


    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.7.3/Chart.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.7.3/Chart.bundle.min.js"></script>

    <script type="text/javascript">

        window.onload = function() {
            var ctx = document.getElementById('myChart').getContext('2d');
            window.myPie = new Chart(ctx, config);
        };

        var colors = ['#F7464A', '#FDB45C', '#949FB1', '#4D5360', '#22601c', '#600d5d', '#2C2660', '#604A21'];

        var config = {
            type: 'pie',
            data: {
                datasets: [{
                    data: [<#list chart as value>${value}, </#list>],
                    backgroundColor: [
                        colors[0], colors[1], colors[2], colors[3], colors[4], colors[5]
                    ]
                }]
            }
        };

    </script>
</body>
</html>