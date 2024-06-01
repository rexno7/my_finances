function displayDoughnutChart(transactions) {
    const merchants = Object.values(transactions).map((x) => x["merchant"]);
    const amounts = Object.values(transactions).map((x) => x["amount"]);
    const totalSpent = new Intl.NumberFormat('en-US', { style: 'currency', currency: 'USD' })
            .format(amounts.reduce((total, amount) => total + amount, 0));

    document.addEventListener('DOMContentLoaded', function () {
        const ctx = document.getElementById('transactionChart').getContext('2d');
        const spendingChart = new Chart(ctx, {
            type: 'doughnut',
            data: {
                labels: merchants,
                datasets: [{
                    data: amounts,
                    backgroundColor: [
                        'rgba(255, 99, 132, 1)',
                        'rgba(54, 162, 235, 1)',
                        'rgba(255, 206, 86, 1)',
                        'rgba(75, 192, 192, 1)',
                        'rgba(153, 102, 255, 1)',
                        'rgba(255, 159, 64, 1)'
                    ],
                    borderColor: [
                        'rgba(255, 99, 132, 1)',
                        'rgba(54, 162, 235, 1)',
                        'rgba(255, 206, 86, 1)',
                        'rgba(75, 192, 192, 1)',
                        'rgba(153, 102, 255, 1)',
                        'rgba(255, 159, 64, 1)'
                    ],
                    borderWidth: 1
                }]
            },
            options: {
                responsive: true,
                plugins: {
                    legend: {
                        display: false,
                        position: 'right',
                    },
                    tooltip: {
                        callbacks: {
                            label: function(context) {
                                return new Intl.NumberFormat('en-US', { style: 'currency', currency: 'USD' })
                                        .format(context.parsed);
                            }
                        },
                        mode: 'point',
                        intersect: false,
                    },
                    title: {
                        display: true,
                        text: 'Total Spending for Month: ' + totalSpent,
                    }
                }
            }
        });
    });
}