import { useEffect, useState } from 'react';
import { getAllSupplies, createSupply, updateSupply, deleteSupply, searchSupplies } from '../api/supplyApi';
import SearchBar from '../components/SearchBar';
import SupplyList from '../components/SupplyList';
import SupplyForm from '../components/SupplyForm';

export default function SuppliesPage() {
    const [items, setItems] = useState([]);
    const [editing, setEditing] = useState(null);

    useEffect(() => {
        load();
    }, []);

    const load = () => getAllSupplies().then(res => setItems(res.data));

    const handleSearch = params =>
        searchSupplies(params).then(res => setItems(res.data));

    const handleSave = item => {
        const promise = item.id ? updateSupply(item.id, item) : createSupply(item);
        promise.then(() => {
            load();
            setEditing(null);
        });
    };

    const handleDelete = id =>
        deleteSupply(id).then(() => load());

    return (
        <>
            <SearchBar onSearch={handleSearch} />
            <SupplyForm
                key={editing?.id || 'new'}
                initialData={editing}
                onSubmit={handleSave}
                onCancel={()=>setEditing(null)}
            />
            <SupplyList
                items={items}
                onEdit={item => setEditing(item)}
                onDelete={handleDelete}
            />
        </>
    );
}
