<div>
    <form action="/yammerNotifier/adminSettings.html" method="post">
        <div>
            <span>Protocol: </span>
            <select name="type">
                <option value="Udp" ${type == 'Udp' ? 'selected="selected"' : ''}>Udp</option>
                <option value="Tcp" ${type == 'Tcp' ? 'selected="selected"' : ''}>Tcp</option>
            </select>
        </div>
        <input type="submit" value="Save">
    </form>
</div>