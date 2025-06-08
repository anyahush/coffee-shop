import { useState } from 'react';

export default function SearchBar({ onSearch }) {
    const [type, setType] = useState('name');
    const [query, setQuery] = useState('');

    const handleSearch = () => {
        onSearch({ type, query });
    };

    return (
        <>
            <select value={type} onChange={e => setType(e.target.value)}>
                <option value="name">Name</option>
                <option value="category">Category</option>
                <option value="supplier">Supplier</option>
            </select>
            <input
                type="text"
                placeholder="Search..."
                value={query}
                onChange={e => setQuery(e.target.value)}
            />
            <button onClick={handleSearch}>Search</button>
        </>
    );
}
