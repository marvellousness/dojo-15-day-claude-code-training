#!/usr/bin/env python3
"""Export the Days and Checkpoints tables from the training progress.md into JSON row files.

The JSON files are uploaded to the Dojo Training Tracker dashboard artifact, so
progress.md stays the single source of truth and the dashboard only mirrors it.

Usage: python3 dashboard/export-progress-tables-to-json.py [progress.md] [out_dir]
"""
import json
import sys
from pathlib import Path

REPO_ROOT = Path(__file__).resolve().parent.parent
DEFAULT_PROGRESS = REPO_ROOT / "plans/261017-1006-dojo-15-day-claude-code-training/progress.md"
DEFAULT_OUT_DIR = REPO_ROOT / "dashboard/data"

# Day number -> phase label, mirrors the phase files in the plan directory.
PHASE_BY_DAY = {
    **{d: "Fundamentals" for d in range(1, 4)},
    **{d: "Desktop core" for d in range(4, 7)},
    **{d: "Skills & MCP" for d in range(7, 9)},
    **{d: "Automate & review" for d in range(9, 12)},
    **{d: "Capstone" for d in range(12, 16)},
}
CHECKPOINT_DAYS = {3: "CP1", 6: "CP2", 11: "CP3", 15: "Final"}


def read_table_after(lines, heading):
    """Return the rows (lists of cell strings) of the first markdown table after `heading`."""
    start = lines.index(heading)
    rows = []
    for line in lines[start + 1:]:
        if line.startswith("## "):
            break
        if line.startswith("|"):
            rows.append([cell.strip() for cell in line.strip().strip("|").split("|")])
    # Drop header and the |---| separator row.
    return rows[2:]


def split_fraction(text):
    """Parse '3/7' into (3, 7)."""
    done, total = text.split("/")
    return int(done), int(total)


def main():
    progress_path = Path(sys.argv[1]) if len(sys.argv) > 1 else DEFAULT_PROGRESS
    out_dir = Path(sys.argv[2]) if len(sys.argv) > 2 else DEFAULT_OUT_DIR
    lines = progress_path.read_text(encoding="utf-8").splitlines()

    days = []
    for day, date, topic, status, tasks, courses, report, evidence, notes in read_table_after(lines, "## Days"):
        done, total = split_fraction(tasks)
        day_number = int(day)
        days.append({
            "day": day_number,
            "date": date.split(" ")[0],
            "weekday": date.split(" ")[1] if " " in date else "",
            "topic": topic,
            "phase": PHASE_BY_DAY[day_number],
            "checkpoint": CHECKPOINT_DAYS.get(day_number, ""),
            "status": status,
            "tasks_done": done,
            "tasks_total": total,
            "courses": courses,
            "report": "" if report == "—" else report,
            "notes": notes,
        })

    checkpoints = []
    for cp, day, status, items, evidence in read_table_after(lines, "## Checkpoints"):
        done, total = split_fraction(items)
        checkpoints.append({
            "checkpoint": cp,
            "day": int(day),
            "status": status,
            "items_done": done,
            "items_total": total,
        })

    out_dir.mkdir(parents=True, exist_ok=True)
    (out_dir / "training-days.json").write_text(json.dumps(days, indent=2) + "\n", encoding="utf-8")
    (out_dir / "training-checkpoints.json").write_text(json.dumps(checkpoints, indent=2) + "\n", encoding="utf-8")
    print(f"wrote {len(days)} days, {len(checkpoints)} checkpoints to {out_dir}")


if __name__ == "__main__":
    main()
