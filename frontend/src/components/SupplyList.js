export default function SupplyList({ items, onEdit, onDelete }) {
    if (items.length === 0) {
        return <p className="text-muted">No supplies found.</p>;
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
                    <td>
                        <button
                            className="btn btn-sm btn-outline-primary mr-2"
                            onClick={()=>onEdit(item)}
                        >
                            Edit
                        </button>
                        <button
                            className="btn btn-sm btn-outline-danger"
                            onClick={()=>onDelete(item.id)}
                        >
                            Delete
                        </button>
                    </td>
                </tr>
            ))}
            </tbody>
        </table>
    );
}
