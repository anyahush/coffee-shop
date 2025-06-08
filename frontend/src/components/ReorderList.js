// src/components/ReorderList.jsx
import React from 'react';

export default function ReorderList({ items }) {
    if (!items.length) {
        return <p className="text-muted">No items need reordering.</p>;
    }

    return (
        <table className="table table-striped table-hover">
            <thead className="thead-light">
            <tr>
                {['ID','Name','Category','Qty','Reorder','Supplier','Actions']
                    .map(h => <th key={h}>{h}</th>)}
            </tr>
            </thead>
            <tbody>
            {items.map(item => (
                <tr key={item.id}>
                    <td>{item.id}</td>
                    <td>{item.name}</td>
                    <td>{item.category}</td>
                    <td>{item.quantityInStock}</td>
                    <td>{item.reorderLevel}</td>
                    <td>{item.supplier?.name}</td>
                </tr>
            ))}
            </tbody>
        </table>
    );
}
