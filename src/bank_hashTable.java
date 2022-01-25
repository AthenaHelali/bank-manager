public class bank_hashTable {
    private list[] table;//keep value
    private int ctr;

    public bank_hashTable() {
        table = new list[30];
    }

    public void put(String key, Bank value) {
        int temp = hash(key);
        list value_list = table[temp];
        while (value_list != null) {
            if (value_list.key.equals(key))
                break;
            value_list = value_list.next;
        }
        if (value_list != null) {
            value_list.value = value;
        } else {
            if (ctr >= 0.8 * table.length) {
                resize();
            }
            list newNode = new list();
            newNode.key = key;
            newNode.value = value;
            newNode.next = table[temp];
            table[temp] = newNode;
            ctr++;
        }
    }

    public Bank get(String key) {
        int temp = hash(key);
        list list = table[temp];
        while (list != null) {
            if (list.key.equals(key))
                return list.value;
            list = list.next;
        }
        return null;
    }

    public void remove(String key) {
        int temp = hash(key);
        if (table[temp] == null) {
            return;
        }
        if (table[temp].key.equals(key)) {
            table[temp] = table[temp].next;
            ctr--;
            return;
        }
        list previous = table[temp];
        list curr = previous.next;  // For traversing the list,
        while (curr != null && !curr.key.equals(key)) {
            curr = curr.next;
            previous = curr;
        }
        if (curr != null) {
            previous.next = curr.next;
            ctr--;
        }
    }

    public int size() {
        return ctr;
    }

    private int hash(Object key) {
        return (Math.abs(key.hashCode())) % table.length;
    }

    private void resize() {
        list[] NewTable = new list[table.length * 2];
        for (int i = 0; i < table.length; i++) {
            list list = table[i];
            while (list != null) {
                list next = list.next;
                int hash = (Math.abs(list.key.hashCode())) % NewTable.length;
                list.next = NewTable[hash];
                NewTable[hash] = list;
                list = next;
            }
        }
        table = NewTable;
    }
}
