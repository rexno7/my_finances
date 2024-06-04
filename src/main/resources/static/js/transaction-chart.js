function displayDoughnutChart(transactions, groupBy, chart) {
    // Destroy chart if already existing
    if (chart !== undefined) {
        chart.destroy();
    }

    // Group the transactions for chart
    var groupedTransactions;
    if (groupBy === "CATEGORY"){
        groupedTransactions = groupToArray(transactions, (transaction) => {
            return transaction.category !== null ? transaction.category.name : "UNDEFINED";
        });
    } else { // "MERCHANT"
        groupedTransactions = groupToArray(transactions, (transaction) => {
            return transaction.nickname === null ? transaction.merchant : transaction.nickname;
        });
    }

    // Prep amounts and total for chart
    const amounts = groupedTransactions.map((x) => x["amount"]);
    const totalSpent = new Intl.NumberFormat('en-US', { style: 'currency', currency: 'USD' })
            .format(amounts.reduce((total, amount) => total + amount, 0));
    
    // Create chart
    const ctx = document.getElementById('transactionChart').getContext('2d');
    const spendingChart = new Chart(ctx, {
        type: 'doughnut',
        data: {
            labels: groupedTransactions.map(txn => [txn.groupName, `Count: ${txn.transactions.length}`]),
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
                    text: 'Total Spending for Period: ' + totalSpent,
                }
            }
        }
    });

    return spendingChart;
}

function groupToArray(transactions, groupNameFunction) {
    const groupedTransactions = transactions.reduce((acc, transaction) => {
        const groupName = groupNameFunction(transaction);
        if (!acc[groupName]) {
            acc[groupName] = [];
        }
        acc[groupName].push(transaction);
        return acc;
    }, {});
    
    return Object.entries(groupedTransactions).map(([groupName, transactions]) => {
        const amount = transactions.reduce((sum, { amount }) => sum + amount, 0);
        return { groupName, amount, transactions };
    }).sort((a, b) => a["amount"] - b["amount"]);
}