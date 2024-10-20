window.onload = function() {

	const unregister = document.querySelector('.btn-unregister');

	unregister.addEventListener('click', function() {

		const confirmed = confirm('정말 탈퇴하시겠습니까?');

		if (confirmed == true) {
			const form = document.createElement('form');
			form.method = 'POST';
			form.action = '/rebook/user/unregister.do';

			document.body.appendChild(form);
			form.submit();
		}
	});
};

document.addEventListener('DOMContentLoaded', function() {
	const calendarEl = document.getElementById('calendar');
	const calendar = new FullCalendar.Calendar(calendarEl, {
		initialView: 'dayGridMonth',
		locale: 'ko',
		events: [
			{ title: '책 읽기', start: '2024-10-22' },
			{ title: '독서 모임', start: '2024-10-24' }
		]
	});
	calendar.render();
});

let year = 0;
let month = 0;

let now = new Date();
year = now.getFullYear();
month = now.getMonth();

function create(year, month) {
	$('#titleCalendar span:first-child span:last-child').text(year + '.' + String(month + 1).padStart(2, '0'));

	let date = new Date(year, month, 1);
	let firstDay = date.getDay();
	let lastDate = new Date(year, month + 1, 0).getDate();

	let temp = '';

	temp += '<tr>';

	for (let i = 0; i < firstDay; i++) {
		temp += '<td></td>';
	}

	//날짜 <td>
	for (let i = 1; i <= lastDate; i++) {
		temp += '<td>';
		temp += `<span class="no" data-date="\${i}">\${i}</span>`;
		temp += '<div>';
		temp += '</div>'
		temp += '</td>';

		if ((i + firstDay) % 7 == 0) {
			temp += '</tr><tr>'
		}
	}

	temp += '</tr>';

	$('#tblCalendar tbody').html(temp);
	/* loadCalendar(year, month); */
}

create(year, month);

$('#btnPrev').click(() => {
	now.setMonth(now.getMonth() - 1);
	year = now.getFullYear();
	month = now.getMonth();
	create(year, month);
});

$('#btnNow').click(() => {
	console.log('cc');
	now = new Date();
	year = now.getFullYear();
	month = now.getMonth();
	create(year, month);
});

$('#btnNext').click(() => {
	now.setMonth(now.getMonth() + 1);
	year = now.getFullYear();
	month = now.getMonth();
	create(year, month);
});